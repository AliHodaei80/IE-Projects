import React from "react";
import { useLocation, useEffect, useState } from "react";
import Header from "./components/header.js";
import { Navigate, useNavigate, useSearchParams } from "react-router-dom";
import Footer from "./components/footer.js";
import { fetchData, postData, sendToast } from "./utils/request_utils.js";
import { useAuth, AuthProvider } from "./context/AuthContext.js";

const callback_path = "/Callback";
const ROLE_MANAGER = "ROLE_manager";

export default function CallBackPage() {
  //   const location = useLocation();
  const [searchParams, setSearchParams] = useSearchParams();
  const { authDetails, setAuthDetails } = useAuth();
  const [userData, setUserData] = useState({});
  const [AxiosResult, setResult] = useState({});

  const navigate = useNavigate();

  function resolve_role(data) {
    const copy_data = data;
    if (data.user.authorities[0].authority === ROLE_MANAGER) {
      copy_data.user.role = "MANAGER";
    } else {
      copy_data.user.role = "CLIENT";
    }
    return copy_data;
  }

  const handleResponse = (response) => {
    console.log(response);
    if ("token" in response) {
      console.log("onLoginSuccess");
      onLoginSuccess(response);
    } else {
      console.log("onLoginFailure");
      onLoginFailure(response);
    }
  };

  const onLoginSuccess = (response) => {
    console.log("responseeeeeeeee" + response);
    console.log(response);
    const NuserData = resolve_role(response);
    setAuthDetails(NuserData);
    NuserData.logged_in = true;
    navigate("/", { replace: true, state: userData });
  };

  const onLoginFailure = (response) => {
    console.log("Login failed not redirecting");
    sendToast(false, response.data);
    navigate("/authenticate", { replace: false, state: userData });
  };

  useEffect(() => {
    // const params = new URLSearchParams(location.search);
    const code = searchParams.get("code");
    if (code) {
      console.log("Authorization code:", code);
    }

    fetchData(
      callback_path + "?code=" + code,
      null,

      handleResponse,
      setResult,
      false
    );
  }, []);

  return (
    <div>
      <Header />
      <main className="flex-grow-1">
        <div className="p-3 container">Waiting...</div>
      </main>

      <Footer />
    </div>
  );
}
