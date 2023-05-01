# Weather App

## Project Description

The Weather App in Kotlin is an application that shows the weather in a given location. The application uses the following technologies:

- View Binding to bind the view to the code
- Clean Architecture to better organize the code and make it more flexible and scalable
- Coroutines for asynchronous operations
- DI Koin for managing dependencies in the application
- Retrofit 2 for API usage
- Weather API https://www.weatherapi.com/ for up-to-date weather information

## Application screenshots
### light theme
  <img src="https://user-images.githubusercontent.com/84407094/235418283-4a00e167-3ef4-42c9-b82e-9b05a4db3f86.png" width="200" />   <img src="https://user-images.githubusercontent.com/84407094/235418368-48fd765a-0ff9-4651-b160-12b244165985.png" width="200" /> <img src="https://user-images.githubusercontent.com/84407094/235418519-2b57867f-5068-40d0-a9a5-d86f26268667.png" width="200" /> <img src="https://user-images.githubusercontent.com/84407094/235418548-fdbe8751-2647-4218-a41c-03c36d38bd60.png" width="200" />

### dark theme
<img src="https://user-images.githubusercontent.com/84407094/235418435-ae13b9f4-a85f-42c0-a226-a7674bf8b653.png" width="200" /> <img src="https://user-images.githubusercontent.com/84407094/235418882-0741aeee-f2a1-42fc-8d80-f216de45d098.png" width="200" /> <img src="https://user-images.githubusercontent.com/84407094/235418493-c0fc70aa-68d1-4dbf-aa19-4562d25eef2d.png" width="200" /> <img src="https://user-images.githubusercontent.com/84407094/235418569-d3297b15-b505-4f2b-8753-90baa39a1051.png" width="200"  /> 

## Application structure.

The application is divided into three layers of architecture: 

- **presentation**: contains the View and Presenter, which are responsible for displaying and managing the user interface
- **domain**: contains the Use Case interfaces, responsible for application activity and fetching data from the server 
- **data**: contains the implementation of the data, including the network layer, database and data source with the weather API https://www.weatherapi.com/

## How to start the application

To run the application you need to:

1. Download the code from the repository
2. Install Android Studio and Android SDK
3. Connect to Internet
4. Run the app on the emulator or on your device

## How to use the application

After launching the app, the weather will appear on the screen. The default setting is Moscow. To change the weather, click on the arrow next to the place name.
