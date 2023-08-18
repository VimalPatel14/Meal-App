<h1>Meal App</h1>

<p>Meal App is a demo Android application that allows users to explore various meal categories, view meals within those categories, mark favorite categories, and search for specific meals by name. The app leverages the MVVM architecture and utilizes the <a href="https://www.themealdb.com/api/json/v1/1/">TheMealDB API</a> to fetch meal data.</p>

<h2>Features</h2>
<ul>
    <li>Bottom Navigation: The app features a bottom navigation bar with three tabs: Home, Search, and Favourites.</li>
    <li>Navigation Graph: The navigation between fragments is managed using the Android Navigation component's navigation graph.</li>
    <li>Home Fragment: Displays a list of meal categories using RecyclerView. Users can mark categories as favorites.</li>
    <li>Favourites Fragment: Shows all the categories that users have marked as favorites. Data is persisted using Room Database.</li>
    <li>Meal List Fragment: Presents a list of meals within a selected category. Users can click on a meal to view its details.</li>
    <li>Meal Details Fragment: Displays detailed information about a selected meal, including its name, image, ingredients, and instructions.</li>
    <li>Search Fragment: Allows users to search for meals by name.</li>
    <li>MVVM Architecture: The app is designed using the Model-View-ViewModel (MVVM) architectural pattern.</li>
    <li>ViewBinding: Utilizes ViewBinding to bind UI components and layout elements.</li>
    <li>Retrofit: Uses Retrofit to make network requests to TheMealDB API and fetch meal data.</li>
    <li>Navigation Component: Uses Android Navigation Component for seamless navigation between fragments.</li>
    <li>Room Database: Implements Room Database to store and retrieve favorite meal categories.</li>
</ul>

<h2>Screenshots</h2>
<table>
  <tr>
    <td><img src="https://github.com/VimalPatel14/Meal-App/blob/master/app/sc1.png" alt="Image 1"></td>
    <td><img src="https://github.com/VimalPatel14/Meal-App/blob/master/app/sc2.png" alt="Image 2"></td>
    <td><img src="https://github.com/VimalPatel14/Meal-App/blob/master/app/sc3.png" alt="Image 3"></td>
    <td><img src="https://github.com/VimalPatel14/Meal-App/blob/master/app/sc4.png" alt="Image 4"></td>
    <td><img src="https://github.com/VimalPatel14/Meal-App/blob/master/app/sc5.png" alt="Image 5"></td>
    <td><img src="https://github.com/VimalPatel14/Meal-App/blob/master/app/sc6.png" alt="Image 6"></td>
  </tr>
</table>

<h2>Usage</h2>
<p>To use the app, you can follow these steps:</p>
<ol>
    <li>Clone the repository: <code>git clone https://github.com/yourusername/meal-app.git</code></li>
    <li>Open the project in Android Studio.</li>
    <li>Build and run the app on an emulator or a physical device.</li>
</ol>

<h2>Dependencies</h2>
<ul>
    <li><a href="https://developer.android.com/topic/libraries/architecture">Android Architecture Components</a></li>
    <li><a href="https://square.github.io/retrofit/">Retrofit</a></li>
    <li><a href="https://github.com/bumptech/glide">Glide</a></li>
    <li><a href="https://developer.android.com/topic/libraries/view-binding">ViewBinding</a></li>
    <li><a href="https://developer.android.com/guide/navigation">AndroidX Navigation Component</a></li>
    <li><a href="https://developer.android.com/topic/libraries/architecture/room">Room Database</a></li>
</ul>

<h2>Credits</h2>
<p>This app uses the <a href="https://www.themealdb.com/api/json/v1/1/">TheMealDB API</a> to fetch meal data. Special thanks to the contributors of TheMealDB for providing this resource.</p>

<h2>License</h2>
<p>This project is licensed under the <a href="LICENSE">MIT License</a>.</p>

<hr>

<p>Feel free to fork and modify this app for your own learning purposes or to adapt it to your specific needs. If you have any questions or suggestions, please open an issue or pull request in the repository. Enjoy exploring the world of meals!</p>

</body>
</html>
