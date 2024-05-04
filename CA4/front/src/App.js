import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Error from "./components/error";
import ManagerRestaurants from "./manager_restaurants";
import ManageRestaurantPage from "./manage_restaurant";
import AuthPage from "./AuthPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/error" element={<Error />} />
        <Route path="/authenticate" element={<AuthPage />} />
        <Route path="/manager-restaurants" element={<ManagerRestaurants />} />
        <Route
          path="/manager-restaurants/:id"
          element={<ManageRestaurantPage />}
        />
      </Routes>
    </Router>
  );
}
export default App;
