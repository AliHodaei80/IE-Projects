import React, { createContext, useState, useContext, useEffect } from "react";
import Cookies from "js-cookie";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authDetails, setAuthDetails] = useState(() => {
    const savedAuthDetails = Cookies.get("authDetails");
    return savedAuthDetails
      ? JSON.parse(savedAuthDetails)
      : { logged_in: false };
  });

  useEffect(() => {
    Cookies.set("authDetails", JSON.stringify(authDetails), { expires: 7 }); 
  }, [authDetails]);

  return (
    <AuthContext.Provider value={{ authDetails, setAuthDetails }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
