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
import CallBackPage from "./CallBackPage";
import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./components/private_route";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/error" element={<Error />} />
          <Route path="/authenticate" element={<AuthPage />} />
          <Route path="/Callback" element={<CallBackPage />} />

          <Route element={<PrivateRoute />}>
            <Route path="/" element={<Home />} />
            <Route path="/search_result" element={<SearchResultPage />} />
            <Route path="/restaurant/:id" element={<RestaurantPage />} />
            <Route
              path="/manager_restaurants"
              element={<ManagerRestaurants />}
            />
            <Route path="/reservations" element={<ReservationsPage />} />
            <Route
              path="/manager_restaurants/:id"
              element={<ManageRestaurantPage />}
            />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
