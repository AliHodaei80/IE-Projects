import React from "react";
import { useState } from "react";
import { postData, sendToast } from "../utils/request_utils.js";
import "bootstrap/dist/js/bootstrap.js";
import "../styles/add_rest_modal.css";

const round_times = [
  "00:00",
  "01:00",
  "02:00",
  "03:00",
  "04:00",
  "05:00",
  "06:00",
  "07:00",
  "08:00",
  "09:00",
  "10:00",
  "11:00",
  "12:00",
  "13:00",
  "14:00",
  "15:00",
  "16:00",
  "17:00",
  "18:00",
  "19:00",
  "20:00",
  "21:00",
  "22:00",
  "23:00",
];
const managerUsername = "ali";
let restaurantBody = {};
function AddRestaurantModal({ fetchRestaurants }) {
  const [name, setName] = useState("");
  const [type, setType] = useState("");
  const [description, setDescription] = useState("");
  const [country, setCountry] = useState("");
  const [city, setCity] = useState("");
  const [street, setStreet] = useState("");
  const [startTime, setStartTime] = useState(round_times.at(0));
  const [endTime, setEndTime] = useState(round_times.at(0));
  const [nameError, setNameError] = useState("");

  const handleRespponse = (response) => {
    console.log(response);
    console.log(response.success);
    if (response.success) {
      console.log(restaurantBody);
      fetchRestaurants();
      sendToast(true, response.data);
    } else {
      console.log(restaurantBody);
      if ("data" in response) {
        sendToast(false, response.data);
      } else if ("errorMessage" in response) {
        sendToast(false, response.errorMessage);
      }
    }
  };

  const handleNameCheckResponse = (response) => {
    console.log("in handleNameCheckResponse");
    console.log(response);
    if (response.success) {
      if (response["data"].restaurants.length !== 0) {
        setNameError("Name is duplicated!");
        console.log(nameError);
      } else {
        setNameError("");
      }
    }
  };
  const handleNameChange = (event) => {
    const newName = event.target.value;
    setName(newName);
    let searchRequestBody = {
      action: "search_by_exact_name",
      search: newName,
    };
    let res = postData(
      "/restaurants/search",
      searchRequestBody,
      handleNameCheckResponse,
      (res) => {},
      (res) => {}
    );
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    restaurantBody = {
      name: name,
      type: type,
      description: description,
      address: {
        country: country,
        city: city,
        street: street,
      },
      startTime: startTime,
      endTime: endTime,
      managerUsername: managerUsername,
    };
    console.log("Name:", name);
    console.log("type:", type);
    console.log("description:", description);
    console.log("country:", country);
    console.log("city:", city);
    console.log("street:", street);
    console.log("startTime:", startTime);
    console.log("endTime:", endTime);
    let res = await postData(
      "/restaurants/add",
      restaurantBody,
      handleRespponse,
      () => {},
      () => {}
    );
  };
  return (
    <>
      {
        <div
          className="modal fade"
          role="dialog"
          id="addRestModal"
          data-bs-backdrop="static"
          data-bs-keyboard="false"
          tabIndex="-1"
          aria-labelledby="staticBackdropLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header align-items-center">
                <h1 className="modal-title fs-5" id="staticBackdropLabel">
                  Add Restaurant
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body">
                <form onSubmit={handleSubmit}>
                  <div className="mb-3 d-flex position-relative">
                    <label
                      htmlFor="rest-name"
                      className="col-form-label col-md-4"
                    >
                      Name
                    </label>

                    <input
                      type="text"
                      className="form-control ms-auto rounded-4"
                      name="name"
                      id="rest-name"
                      value={name}
                      onChange={handleNameChange}
                    />
                    {nameError && (
                      <div className="text-danger position-absolute">
                        {nameError}
                      </div>
                    )}
                  </div>

                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="rest-type"
                      className="col-form-label col-md-4"
                    >
                      Type
                    </label>

                    <input
                      type="text"
                      className="form-control ms-auto rounded-4"
                      id="rest-type"
                      name="type"
                      value={type}
                      onChange={(e) => setType(e.target.value)}
                    />
                  </div>
                  <div className="mb-3">
                    <label
                      htmlFor="rest-description"
                      className="col-form-label col-md-4"
                    >
                      Description
                    </label>

                    <textarea
                      className="form-control rounded-4"
                      id="rest-description"
                      name="description"
                      placeholder="Type about restaurant..."
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                    ></textarea>
                  </div>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="rest-country"
                      className="col-form-label col-md-4"
                    >
                      Country
                    </label>

                    <input
                      type="text"
                      className="form-control ms-auto rounded-4"
                      id="rest-country"
                      name="country"
                      value={country}
                      onChange={(e) => setCountry(e.target.value)}
                    />
                  </div>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="rest-city"
                      className="col-form-label col-md-4"
                    >
                      City
                    </label>

                    <input
                      type="text"
                      className="form-control ms-auto rounded-4"
                      id="rest-city"
                      name="city"
                      value={city}
                      onChange={(e) => setCity(e.target.value)}
                    />
                  </div>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="rest-street"
                      className="col-form-label col-md-4"
                    >
                      Street
                    </label>

                    <input
                      type="text"
                      className="form-control ms-auto rounded-4"
                      id="rest-street"
                      name="street"
                      value={street}
                      onChange={(e) => setStreet(e.target.value)}
                    />
                  </div>

                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="rest-starttime"
                      className="col-form-label col-md-4"
                    >
                      Start Hour
                    </label>

                    <select
                      className="form-select rounded-4 ms-auto"
                      id="rest-starttime"
                      name="startTime"
                      aria-placeholder={round_times.at(0)}
                      value={startTime}
                      onChange={(e) => setStartTime(e.target.value)}
                    >
                      {round_times.map((round_time) => (
                        <option value={round_time}>{round_time}</option>
                      ))}
                    </select>
                  </div>

                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="rest-endtime"
                      className="col-form-label col-md-4"
                    >
                      End Hour
                    </label>

                    <select
                      className="form-select rounded-4 ms-auto"
                      id="rest-endtime"
                      name="endTime"
                      aria-placeholder={round_times.at(0)}
                      value={endTime}
                      onChange={(e) => setEndTime(e.target.value)}
                    >
                      {round_times.map((round_time) => (
                        <option value={round_time}>{round_time}</option>
                      ))}
                    </select>
                  </div>
                  <button
                    type="submit"
                    id="add-rest-submit-butoon"
                    className="btn w-100 btn-primary rounded-3"
                    data-bs-dismiss="modal"
                    disabled={
                      nameError ||
                      !name ||
                      !type ||
                      !description ||
                      !country ||
                      !city ||
                      !street
                    }
                  >
                    Add
                  </button>
                </form>
              </div>
              <div className="modal-footer"></div>
            </div>
          </div>
        </div>
      }
    </>
  );
}

export default AddRestaurantModal;
