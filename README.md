# IOT-based Oximeter assisted Sleep Apnea Android Application with Machine Learning.

Through study of research papers related to studies conducted around Sleep Apnea syndrome, project
identified by viewing the data of the population affected by this particular disease and several studies that
it find common factors including Age,Gender, Diabetic Condition, Drug Symptoms,Breathing Disorder,
Location and Spo2 levels  that contribute towards Sleep Apnea disorder. 

Hence find data related to the disease and prepared it neat and clean and then taking input of User data including manual entrance of
age,gender,diabetic status, Drug Addiction and Breathing Disorder, Location through User’s Gps and
collection of spo2 through an IOT-based Oximeter sensor into the Sleep Apnea Android App, prepared a
Machine Learning Model and trained it using the collected data to predict whether a patient is suffering
with Sleep Apnea Disorder or not and displayed the results to the user on Android Application.

## Objectives

### *1. To build an IOT based sensor to be used as an oximeter that inputs user Spo2 levels and BPM level and saves it to a database.*

->To input Spo2 levels developed an IOT-based Oximeter Sensor using MAX30100 Pulse Oximeter Sensor and ESP8266 WiFi MCU(NodeMCU) setup circuited on breadboard and 
connected to NodeMcu Arduino Ide via USBports. These readings were saved as data in our Sql database model on Xampp Local server.



### *2.To Find Gps location of User to use his location as a parameter for prediction.*

->To find GPS location,the project identifies the device's physical location using Android Location Service.
In the project, User permission is given and a function is created to enable GPS service and retrieve
location information in the form of Location_Manager variable through which identify and retrieve
longitude and latitude details. Using these coordinates, the project finds the State and City of the device
and displays them on User Interface.



### *3. To build and train a machine learning model to predict whether a person suffering from sleep apnea or not using suitable cleaned data.*

->To predict whether a person is diagnosed with Sleep Apnea found machine learning to be
an effective tool to train the data and hence developed an machine learning model.For this
collected information on various models that can be used for binary classification of a dataset
among them decided to work on Neural Networks



### *4. To build an Android Application that takes user data from a database and print the result after successful prediction by Machine learning model.*

->The Android Application has been developed using Java as the programming language and XML for
layout using Android Studio Software. The App has activities including Login Activity and Register
Activity with data transaction and validation to and from SQLITE database which is locally stored in the
Android.User Session is saved using SharedPreferences and Logout function is Provided in the Profile Fragment
of the Application which allows the user to upload Profile Picture and displays the logged in username.
