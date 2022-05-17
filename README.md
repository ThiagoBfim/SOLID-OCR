## Applying SOLID with OCR project

In this project we apply SOLID to build a REST API to receive an Image and return the text from this Image.

### This project have three big goals:

* Use SOLID an Clean code to create a good solution to our problem.
* Use GCP(Google Cloud Platform) with Spring Boot to create services with Machine Learning.
* Use Cloudinary service to help with text extraction.

### Tasks:
 
- [x] Create an endpoint that receives an image and extracts the text from it.
- [x] Include the Google OCR and execute the Cloudinary service if Google's service is offline.
- [x] Include the Cotes Storage to prevent charge :)
- [x] Include Text in Storage to be quickly search
- [x] Include another OCR service


<img width="800" src="images/9_DIP.png?raw=true" alt='Project UML'>

To access the UML go to [Draw.io](https://www.draw.io/) and upload this file [SOLID](images/SOLID.drawio)

### Technologies:

* Java 11
* Spring MVC
* Spring Cloud GCP
* Junit

### References 

* [Cloudinary Service](https://cloudinary.com/invites/lpov9zyyucivvxsnalc5/tax5t3eafznxwitsghjy?t=default)
* [Cloud Vision API - Price](https://cloud.google.com/vision/pricing#prices)
* [Cloud Vision API - GitHub Examples](https://github.com/googleapis/java-vision/)
