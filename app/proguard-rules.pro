-keep class dagger.** { *; }
-keep interface dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.MembersInjector { *; }
-keep class * implements dagger.Component { *; }
-keep class * implements dagger.Module { *; }
-keep class * implements dagger.Provides { *; }

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
 -keepclassmembers class com.capstone.submissionexpertone.core.data.source.remote.response.MovieListResponse {
     <fields>;
     <methods>;
 }

 -keep class com.capstone.submissionexpertone.core.data.source.remote.response.** { *; }
 -keep class com.capstone.submissionexpertone.core.data.source.remote.response.MovieResponse { *; }
-dontwarn java.lang.invoke.StringConcatFactory
