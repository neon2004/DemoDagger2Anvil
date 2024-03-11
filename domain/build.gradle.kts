@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("kotlin")
    alias(libs.plugins.anvil)


}


dependencies{

    implementation(libs.javax.inject)
    implementation(libs.coroutines.android )

    implementation (libs.coroutines.core)
    implementation(libs.androidx.annotation.jvm)

    implementation (libs.dagger)
    implementation(libs.anvil.annotations)
}
