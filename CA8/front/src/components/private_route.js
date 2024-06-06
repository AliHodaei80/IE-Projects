import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import Cookies from "js-cookie";
import { useAuth } from "../context/AuthContext";

const PrivateRoute = () => {
  const { authDetails, setAuthDetails } = useAuth();
  return authDetails.token !== undefined ? (
    <Outlet />
  ) : (
    <Navigate to="/authenticate" />
  );
};

export default PrivateRoute;
