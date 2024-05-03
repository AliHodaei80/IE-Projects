import axios from "axios";
// TODO Probably add url parameters to fetch data (or alter url your self ?)
export function fetchData(url, payload, resultSetter, onCompleted) {
  console.log("Fetching data from url", url, "with payload", payload);
  axios
    .post(url, payload, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("Data:", response.data);
      resultSetter({ success: true });
      onCompleted();
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

export function postData(url, payload, resultSetter, onSuccess,onFailure) {
  console.log("Sending data to url", url, "with payload", payload);
  axios
    .post(url, payload, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("Data:", response.data);
      resultSetter({ success: true });
      onSuccess();
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
      onFailure();
    });
}
