plugins {
    id("java")
}

interface PropertySpec {
    @get:Input
    val key: Property<String>

    @get:Input
    @get:Optional
    val value: Property<String?>
}

abstract class TestTask : DefaultTask() {
    @get:Input
    abstract val properties: ListProperty<PropertySpec>

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun doSomething() {
    }
}

tasks.register("dummy", TestTask::class.java) {
    outputFile.set(file("file.xml"))
    properties.addAll(provider {
        val objectFactory = project.objects
        listOf(
            objectFactory.newInstance(PropertySpec::class.java).apply {
                key.set("key")
                value.set(null as String?)
            }
        )
    })
}
