node {
    stage 'setup'
    def mainBranch = "develop"
    def mainBuildFlavor = "Debug"
    def mainBuildType = ""
    def mainBuildVariant = mainBuildFlavor + mainBuildType
    def buildVariants = ["Debug"]
    checkout scm
    properties([
            buildDiscarder(logRotator(numToKeepStr: '10')),
            disableConcurrentBuilds()
    ])
    def revisionHash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def revCount = sh(script: "git rev-list $revisionHash..origin/$mainBranch --count", returnStdout: true)
            .trim().toInteger()
    if (revCount > 0) {
        echo "branch is behind develop, marking build as UNSTABLE"
        currentBuild.result = "UNSTABLE"
    }
    sh "chmod +x ./gradlew"
    gradleNoDaemon('clean')

    stage 'test'
    gradleNoDaemon("test${mainBuildVariant}")
    step([$class: 'JUnitResultArchiver', testResults: '**/app/build/test-results/**/TEST-*.xml'])

    stage 'assemble'
    def assembleCommand = ""
    for (String variant : buildVariants) {
        assembleCommand += "assemble$variant "
    }
    gradleNoDaemon(assembleCommand)
    archive '**/app/build/outputs/apk/app*.apk'

    stage 'analysis'
    gradleNoDaemon(":app:lint${mainBuildVariant} :app:findbugsXml")
    sh 'cloc --by-file --xml --out=app/build/reports/cloc.xml app/src'

    stage 'publish analysis'
    step([$class: 'LintPublisher', pattern: '**/lint-results*.xml'])
    step([$class: 'FindBugsPublisher', pattern: '**/app/build/reports/findbugs/*.xml'])
    def variantClassPath = mainBuildFlavor.substring(0, 1).toLowerCase() + mainBuildFlavor.substring(1)
    step([$class          : 'JacocoPublisher', classPattern: "app/build/intermediates/classes/${variantClassPath}/${mainBuildFlavor.toLowerCase()}",
          exclusionPattern: '**/R.class, **/R$*.class, **/BuildConfig.*, **/Manifest*.*, '
                  // Misc library specific
                  + '**/*$ViewInjector*.*, **/*_*, **/Dagger*, '
                  + 'io/realm/**, **/com/viewpagerindicator/*, **/*$ViewBinder*.*, '
                  // Android specific
                  + '**/*LinearLayout.*, **/*FrameLayout.*, **/*RelativeLayout.*, **/*ImageView.*, '
                  + '**/*ConstraintLayout.*, **/*GridLayout.*, **/*TableLayout.*, **/*ListView.*, '
                  + '**/*ScrollView.*, **/*WebView.*, **/*SearchView.*, **/*VideoView.*, **/*SurfaceView.*, '
                  + '**/*NestedScrollView.*, **/*Toolbar.*, **/*CardView.*, **/*FloatingActionButton.*, '
                  + '**/*RecyclerView.*, **/*ProgressBar.*, **/*Button.*, **/*TabLayout.*, '
                  + '**/*ViewGroup.*, **/*CoordinatorLayout.*, **/*Spinner.*, **/*CheckBox.*, '
                  + '**/*TextView.*, **/*RadioButton.*, **/*SeekBar.*, '
                  + '**/*Activity.*, **/*Fragment.*, **/*Screen*, **/*Application.*, '
                  + '**/*Adapter*.*, **/*ViewHolder*.*, **/*Binder.*, **/*BinderImpl.*, '
                  + '**/*ItemDecorator.*, **/*ItemDecoration.*, '
                  // Hax
                  + '**/*ExtensionKt*, '
                  + '**/*Activity$*, **/*Fragment$*, **/*Application$*, '
                  + '**/*Adapter$*, **/*ViewHolder$*, **/*Binder$*, **/*BinderImpl$*, '
                  + '**/*Companion$CREATOR*, '
                  + '**/*ProgressBar$*, **/*TabLayout$*, **/*RecyclerView$*, '
                  + '**/*ItemDecorator$*, **/*ItemDecoration$*, ',
          execPattern     : 'app/build/jacoco/**.exec',
          inclusionPattern: '**/*.class'])
}

void gradleNoDaemon(String tasks) {
    gradle(tasks, '-Dorg.gradle.daemon=false')
}

void gradle(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks

    if (switches != null) {
        gradleCommand += ' '
        gradleCommand += switches
    }

    sh gradleCommand.toString()
}