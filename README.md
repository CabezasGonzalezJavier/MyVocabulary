# MyVocabulary
My Vocabulary is android app about storage your vocabulary for learning new language or learning more vocabulary in your mother language.


<p align="center">
    <img src="https://github.com/CabezasGonzalezJavier/MyVocabulary/blob/master/MyVocabulary.jpg" alt="Web Launcher"/>
</p>


There is simple implementation of the Model-View-Presenter pattern with no architectural frameworks.

* **[Android architecture](https://github.com/googlesamples/android-architecture)**


Each feature has:

* A contract defining the view and the presenter.
* An Activity which is responsible for the creation of fragments and presenters
* A Fragment which implements the view interface.
* A presenter which implements the presenter interface.

In general, the business logic lives in the presenter and relies on the view to do the Android UI work.

The view contains almost no logic: it converts the presenter's commands to UI actions and listens to user actions, which are passed to the presenter.

Contracts are interfaces used to define the connection between views and presenters.

 
  Libraries
---------
The project is setup using:
 * **[Butter Knife](https://github.com/JakeWharton/butterknife)**
 * **[Espresso](https://google.github.io/android-testing-support-library/docs/espresso/index.html)**
 * **[Mockito](http://mockito.org/)**
 * **[JUnit](http://http://junit.org/junit4/)**
 

# Requirements

    - Android Studio

    - Gradle

# Installation

    - Install Android Studio:

    https://developer.android.com/sdk/installing/index.html

    - Install gradle:

    http://gradle.org/docs/current/userguide/installation.html

# Usage
    Compile with Android Studio and gradle


Feel free to contribute, and contact me for any issues.

Developed By
------------
* Javier González Cabezas - <javiergonzalezcabezas@gmail.com>

<a href="https://es.linkedin.com/in/javier-gonz%C3%A1lez-cabezas-8b4b2231">
  <img alt="Add me to Linkedin" src="https://github.com/JorgeCastilloPrz/EasyMVP/blob/master/art/linkedin.png" />
</a>

License
-------

    Copyright 2016 Javier González Cabezas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
