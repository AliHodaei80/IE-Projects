import React, { createContext, useState, useContext } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authDetails, setAuthDetails] = useState({ logged_in: false });

  return (
    <AuthContext.Provider value={{authDetails, setAuthDetails}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
