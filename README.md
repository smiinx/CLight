# CLight
> CLight is a Java library, with some useful features.

* Version 0.1.0

* You can:
 * Read the code of a website,
 * Encrypt & Decrypt Strings,
 * Cleanup Files,
 * and more.
This Library gets updated regularly, where bugs will be fixed and new features will be added.

![](utils/clight.png)

## Installation

_Eclipse_
1. Right Click on your project
2. Go to 'Build Path'
3. Click 'Configure Build Path'
4. Click 'Add External Jars'
5. Select the clight.jar
6. Click 'Apply and Close'

_IntelliJ_
1. Click 'Project Structure'
2. Go to 'Libraries'
3. Click the '+'
4. Select 'Java'
5. Select the clight.jar
6. Click 'OK'

## Usage examples

* Reading the Website content:
  `String websiteContent = Utils.readWebsiteContent(_WebsiteURL_ "https://google.com");`
* Reading the content of a file:
  `String fileContent = FileManager.getFileContent(_File_ file);`
* Encrypting Strings: 
  `String encrypted = Utils.encryptString(_Text to Encrypt_"Hello World!", _Key_11);`
  _Result: spwwz…hzcwo!_

## Release History

* 0.1.0
    * The first GitHub release.
