import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Error from "./components/error";
import Signup from "./components/signup";
import ManagerRestaurants from "./manager_restaurants";
import AuthPage from "./components/authPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/error" element={<Error />} />
        <Route path="/authenticate" element={<AuthPage />} />
        <Route path="/manager-restaurants" element={<ManagerRestaurants />} />
      </Routes>
    </Router>
  );
}
export default App;
