# Weather App in Kotlin

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
  <img src="https://user-images.githubusercontent.com/84407094/235374968-23ceaa99-a5c9-449f-afe5-2c1cdce2f890.png" width="200" />   <img src="https://user-images.githubusercontent.com/84407094/235374920-f32901d2-cecd-4a18-a848-7e1699b01236.png" width="200" /> <img src="https://user-images.githubusercontent.com/84407094/235374833-76547edf-3286-4333-86dd-77db76ba4ebd.png" width="200" />

### dark theme

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
