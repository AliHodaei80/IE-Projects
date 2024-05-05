import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Error from "./components/error";
import ManagerRestaurants from "./manager_restaurants";
import ManageRestaurantPage from "./manage_restaurant";
import AuthPage from "./AuthPage";
import SearchResultPage from "./SearchResultPage";
import RestaurantPage from "./RestaurantPage";
import ReservationsPage from "./reservations";
import { AuthProvider, useAuth } from "./context/AuthContext";
import { BrowserRouter, Link } from "react-router-dom";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Link to="/restaurant">Go to Other Component</Link>
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/error" element={<Error />} />
          <Route path="/authenticate" element={<AuthPage />} />
          <Route path="/search_result" element={<SearchResultPage />} />
          <Route path="/restaurant" element={<RestaurantPage />} />
          <Route path="/manager-restaurants" element={<ManagerRestaurants />} />
          <Route path="/reservations" element={<ReservationsPage />} />

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
