import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Error from "./components/error";
import ManagerRestaurants from "./manager_restaurants";
import ManageRestaurantPage from "./manage_restaurant";
import AuthPage from "./AuthPage";
import SearchResultPage from "./SearchResultPage";
import RestaurantPage from "./RestaurantPage";
import { AuthProvider, useAuth } from "./context/AuthContext";
function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/error" element={<Error />} />
          <Route path="/authenticate" element={<AuthPage />} />
          <Route path="/search_result" element={<SearchResultPage />} />
          <Route path="/restaurant" element={<RestaurantPage />} />
          <Route path="/manager-restaurants" element={<ManagerRestaurants />} />
          <Route
            path="/manager-restaurants/:id"
            element={<ManageRestaurantPage />}
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}
export default App;
