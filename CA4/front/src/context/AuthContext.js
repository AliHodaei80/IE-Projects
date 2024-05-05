import React, { createContext, useState, useContext, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authDetails, setAuthDetails] = useState(() => {
    const savedAuthDetails = localStorage.getItem("authDetails");
    return savedAuthDetails
      ? JSON.parse(savedAuthDetails)
      : { logged_in: false };
  });
  useEffect(() => {
    localStorage.setItem("authDetails", JSON.stringify(authDetails));
  }, [authDetails]);

  return (
    <AuthContext.Provider value={{ authDetails, setAuthDetails }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
