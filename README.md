# Android Offline Caching Sample

A simple demo project based on Offline Caching using Room.

Simple app gets its data from REST API and caches them phone storage locally.

This app is built by MVVM architecture. This app gets its data from REST API with the help of Retrofit2 and Kotlin Coroutines and caches them to Room database. NetworkBoundResource helper method is implemented with the help of Kotlin Flow. Dependencies are injected by Hilt.

<br><br>
The demo data used [FakeStoreAPI](https://github.com/keikaavousi/fake-store-api).
<br><br>

Libraries used on the sample project ✨
------------------------------------
 * Room
 * LiveData
 * Hilt
 * Coroutines
 * ViewModel
 * Retrofit
 * Glide

<br><br>
# Demo 🚀
![demo](https://user-images.githubusercontent.com/74607521/205621410-2017d134-38bf-4769-b960-2165e9cf3354.png)
<br><br>

Developed By
------------------------------------
* NoKyungEun - <eunn.dev@gmail.com> 

License
------------------------------------
    Copyright 2022 NoKyungEun

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
