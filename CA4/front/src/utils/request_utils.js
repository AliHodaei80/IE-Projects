import axios from "axios";

export function fetchData(url) {
  console.log("Fetching data from url");
  axios
    .get(url)
    .then((response) => {
      console.log("Data:", response.data);
    })
    .catch((error) => {
      if (error.response) {
        console.log("Error code:", error.response.status);
        console.log("Error message:", error.response.data.message);
        return {
          errorCode: error.response.status,
          errorMessage: error.response.data.message,
        };
      } else if (error.request) {
        console.log("Error message:", error.message);
        return { errorCode: null, errorMessage: error.message };
      } else {
        console.log("Error:", error.message);
        return { errorCode: null, errorMessage: error.message };
      }
    });
}

export function postData(url, payload, resultSetter) {
  console.log("Sending data to url", url, "with payload", payload);
  axios
    .post(url, payload, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("Data:", response.data);
      resultSetter({ success: true});
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
        return {
          success: false,
          errorCode: error.response.status,
          errorMessage: error.response.data.message,
        };
      } else if (error.request) {
        resultSetter({ errorCode: null, errorMessage: error.message });
        console.log("Error message:", error.message);
        return { success: false, errorCode: null, errorMessage: error.message };
      } else {
        console.log("Error:", error.message);
        resultSetter({ errorCode: null, errorMessage: error.message });
        return { success: false, errorCode: null, errorMessage: error.message };
      }
    });
}
