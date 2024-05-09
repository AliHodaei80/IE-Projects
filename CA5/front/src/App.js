import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Error from "./components/error";
import ManagerRestaurants from "./ManagerRestaurants";
import ManageRestaurantPage from "./ManageRestaurant";
import AuthPage from "./AuthPage";
import SearchResultPage from "./SearchResultPage";
import RestaurantPage from "./RestaurantPage";
import ReservationsPage from "./reservations";
import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/error" element={<Error />} />
          <Route path="/authenticate" element={<AuthPage />} />
          <Route path="/search_result" element={<SearchResultPage />} />
          <Route path="/restaurant/:id" element={<RestaurantPage />} />
          <Route path="/manager_restaurants" element={<ManagerRestaurants />} />
          <Route path="/reservations" element={<ReservationsPage />} />
          <Route
            path="/manager_restaurants/:id"
            element={<ManageRestaurantPage />}
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}
export default App;
