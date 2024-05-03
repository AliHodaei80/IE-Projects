import axios from "axios";
import { toast } from "react-toastify";
// TODO Probably add url parameters to fetch data (or alter url your self ?)
export function fetchData(url, payload, resultSetter, onCompleted) {
  console.log("Fetching data from url", url, "with payload", payload);
  axios
    .get(url, payload, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("Data:", response.data);
      resultSetter(response.data);
      onCompleted(response.data);
    })
    .catch((error) => {
      if (error.response) {
        console.log("Error code:", error.response.status);
        console.log("Error message:", error.response.data.data);
        resultSetter({
          success: false,
          errorCode: error.response.status,
          errorMessage: error.response.data.data,
        });
      } else if (error.request) {
        resultSetter({ errorCode: null, errorMessage: error.message });
        console.log("Error message:", error.message);
      } else {
        console.log("Error:", error.message);
        resultSetter({ errorCode: null, errorMessage: error.message });
      }
      onCompleted();
    });
}

export function postData(url, payload, resultSetter, onSuccess, onFailure) {
  console.log("Sending data to url", url, "with payload", payload);
  axios
    .post(url, payload, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("Data:", response.data);
      resultSetter(response.data);
      onSuccess(response.data);
    })
    .catch((error) => {
      if (error.response) {
        console.log("Error code:", error.response.status);
        console.log("Error message:", error.response.data.data);
        resultSetter({
          success: false,
          errorCode: error.response.status,
          errorMessage: error.response.data.data,
        });
      } else if (error.request) {
        resultSetter({
          success: false,
          errorCode: null,
          errorMessage: error.message,
        });
        console.log("Error message:", error.message);
      } else {
        console.log("Error:", error.message);
        resultSetter({
          success: false,
          errorCode: null,
          errorMessage: error.message,
        });
      }
      onFailure({
        success: false,
        errorCode: null,
        errorMessage: error.message,
      });
    });
}

export function sendToast(success, message) {
  const options = {
    position: toast.POSITION.BOTTOM_RIGHT,
    autoClose: 3000,
    hideProgressBar: true,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
  };
  console.log("Sending toast");
  if (success) {
    toast.success(message, {
      ...options,
      className: "toast-success",
      bodyClassName: "toast-success-body",
      progressClassName: "toast-success-progress",
    });
  } else {
    toast.error(message, {
      ...options,
      className: "toast-error",
      bodyClassName: "toast-error-body",
      progressClassName: "toast-error-progress",
    });
  }
}
