import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import Cookies from "js-cookie";
import { useAuth } from "../context/AuthContext";

const PrivateAuthRoute = () => {
  const { authDetails, setAuthDetails } = useAuth();
  return authDetails.token !== undefined ? <Navigate to="/" /> : <Outlet />;
};

export default PrivateAuthRoute;
