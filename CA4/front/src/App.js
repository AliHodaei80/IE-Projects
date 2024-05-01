import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Error from "./components/error";
import Login from "./components/login";
import Signup from "./components/signup";
import ManagerRestaurtns from "./manager_restaurants";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/error" element={<Error />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/manager-restaurants" element={<ManagerRestaurtns />} />
      </Routes>
    </Router>
  );
}
export default App;
