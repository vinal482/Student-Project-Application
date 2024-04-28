## An installation guide: Node.js, React-Native and Android Development Toolkit.
The first thing you will need is VS-Code so make sure you have installed it.
Now we will install node.js. Just go on google and install the latest version of node from https://nodejs.org/en. Make sure to install node package manager(npm) as well. After that, install the latest version of Java (Jdk) from OpenLogic.
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/8c4bb117-6d50-45fc-92aa-61f098896b54)

While installing OpenLogic's Java, make sure to set the JAVA_HOME variable and also enable the javasoft (oracle) registry keys. If this is not set, you can also define a new JAVA_HOME variable in user and system (environment) variables and set the path as "C:\Program Files\OpenLogic\jdk-17.0.9.9-hotspot\ "
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/273fce45-ba68-4182-94bf-656368ed4f72)

After installing all, check if they are properly installed by using the command --version in command prompt.
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/31fa16fd-9976-4611-af7e-df7b9213b126)


### Now let us install Android Studio. Go to https://developer.android.com/studio and download the latest version ("Hedgehog" as of now). Make sure to install it in the C drive and make some space beforehand as it does require a lot of space. Once installed, open it, go to More Actions and open the SDK Manager. There check all the boxes from SDK platforms and from SDK tools as shown below.
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/f8d7bbc4-eb64-4052-aee8-798fd8f6d160)

![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/ad10fb6e-6ddb-4cac-9b6b-1597fa6649f6)

Make sure your Android SDK location is set like me. Click on Apply and wait. Now you will need to set some environment variables for your android studio to run properly. Go to environment variables and set new paths as shown below.
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/947bd258-dfc4-4480-9bf7-aa8253ce8571)

Once the environment variables are set, we can get started with creating our own project. (Refer to my other post for more info on the structure of a typical project.)

### Setting your Android phone up:
If you directly want to connect your android phone with the computer and get started building your app, you need a data transfer cable and you need to configure the developer options on your android.

First get promoted to a developer by repeatedly clicking on the "android version" option in your "About Phone" settings. Once you become a developer, go to the developer options and turn on the USB Debugging feature along with the "Keep display on while charging" option. Also set the USB Configuration to "MTP: Media Transfer Protocol". You can check whether your phone is connected or not through adb devices command.
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/d43e90cb-0ce4-4c2a-88ee-cd42d16ad437)

Now open command prompt in any folder that you like and create a new project using the command: npx react-native init project_name
This will take some time but once it is installed, go in the project_name folder and run the command: npm run start (if it doesn't work, try: npx react-native run-android)
Your app will be up and running now.

You might encounter an error of SDK Location not found while trying to run your project. This occurs because a local.properties file is missing in your project. To resolve this, just create a new local.properties file in the android folder of your project and in it provide the path as shown below
![image](https://github.com/vinal482/Student-Project-Application/assets/122971842/695d0aec-b660-4d9c-9ee8-d568440c0466)

The project will run just fine now.



