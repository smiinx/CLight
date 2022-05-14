# CLight
> CLight is a Java library, with some useful features.

* You can:
 * Read the code of a website,
 * Encrypt & Decrypt Strings,
 * Cleanup Files,
 * and more.
This Library gets updated regularly, where bugs will be fixed and new features will be added.

**Disclaimer: CLight is completely free to use and doesnt require any permission from it's owner to use.**

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
  * `String websiteContent = Utils.readWebsiteContent("https://google.com");`
* Reading the content of a file:
  * `String fileContent = FileManager.getFileContent(file);`
* Encrypting Strings: 
  * `String encrypted = Utils.encryptString("Hello World!", 11);`
  * _Result: spwwz…hzcwo!_

## Dependencies

* For the 'Minecraft/Bukkit' interface, you need to download craftbukkit. You can download it [here](https://cdn.getbukkit.org/craftbukkit/craftbukkit-1.12.2.jar). Then you need to add it to your Build Path.

## Release History

* 0.1.0
    * The first GitHub release.
* 0.1.1
    * UPDATE: _Throwing Exceptions at Decrypting and Enrypting_
    * ADDITION: _Added download feature_
