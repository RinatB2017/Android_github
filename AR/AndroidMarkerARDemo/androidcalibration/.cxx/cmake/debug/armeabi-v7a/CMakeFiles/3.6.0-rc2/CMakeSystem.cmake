set(CMAKE_HOST_SYSTEM "Linux-5.5.2-gentoo-r1-x86_64")
set(CMAKE_HOST_SYSTEM_NAME "Linux")
set(CMAKE_HOST_SYSTEM_VERSION "5.5.2-gentoo-r1-x86_64")
set(CMAKE_HOST_SYSTEM_PROCESSOR "x86_64")

include("/opt/android-sdk-update-manager/ndk-bundle/build/cmake/android.toolchain.cmake")

set(CMAKE_SYSTEM "Android-1")
set(CMAKE_SYSTEM_NAME "Android")
set(CMAKE_SYSTEM_VERSION "1")
set(CMAKE_SYSTEM_PROCESSOR "armv7-a")

set(CMAKE_CROSSCOMPILING "TRUE")

set(CMAKE_SYSTEM_LOADED 1)
