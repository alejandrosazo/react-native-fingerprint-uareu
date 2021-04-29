
# react-native-fingerprint-uareu-v1

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
	<li><a href="#important-settings">Important Settings</a></li>
    <li><a href="#usage">Usage</a></li>
	<li><a href="#implementation-in-android">Implementation in Android</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
This package is a bridge to the uareu library developed for Android in Java.

Allows a mobile application developed in React Native to use the external fingerprint reader digitalPersonal 4500.

<strong> This project works only on Android, in some future it could be implemented for IOS </strong>

<!-- GETTING STARTED -->
## Getting started

	`$ npm install react-native-fingerprint-uareu-v1 --save`


If you have problems with the installation try the following command:
	
	`$ npm install react-native-fingerprint-uareu-v1 --force`


### Mostly automatic installation (optional)

	`$ react-native link react-native-fingerprint-uareu-v1`


<!-- IMPORTANT SETTINGS -->
## Important Settings 

1. Open up `android/app/src/main/AndroidManifest.xml`
  	- Change or Add this line:  
  ```XML
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  		package="com.[yourprojectname]">
		   // .. rest of your code 
<application
		   // .. rest of your code 

		android:allowBackup="true"  <-- Change this line to "true" or add.
/>
		   // .. rest of your code 
</application>
</manifest>
  ```
  	

<!-- USAGE -->
## Usage
```javascript
import FingerPrintUareu from 'react-native-fingerprint-uareu-v1'

const App = () => {
  const [fingerPrintPick, setFingerPrintPick] = useState('');

  const scanFinger = async () => {
    var { encoded, convertBase64 } = await FingerPrintUareu.startScan();
	//var convertBase64 is encoded with WSQEncoder
	//var encoded is encoded in base64
    setFingerPrintPick(encoded);
  }

  return (
    <SafeAreaView>
		/* Change this implementation at your convenience */
      <Text>Finger: {fingerPrintPick}</Text>
      <Button
        onPress={() => scanFinger()}
        title="Escanear"
      />

    </SafeAreaView>
  );
};
```

<!-- IMPLEMENTATION IN ANDROID -->
## Implementation In Android
If you want to build the same project exclusively for Android check out my repository: 

* [https://github.com/alejandrosazo/fingerprint-reader-uareu-android](https://github.com/alejandrosazo/fingerprint-reader-uareu-android)
<!-- CONTACT -->
## Contact

Alejandro Sazo - ottoalejandrobonilla2014@gmail.com

Project Link: [https://github.com/alejandrosazo/react-native-fingerprint-uareu](https://github.com/alejandrosazo/react-native-fingerprint-uareu)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
Special thanks to Steven Hodgson steven@kanopi.asia.

The base code can be found in the following repository:
* [uareu](https://github.com/shodgson/uareu)
