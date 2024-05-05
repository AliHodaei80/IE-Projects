import React from "react";
import { useState } from "react";
import { postData, sendToast } from "../utils/request_utils.js";
import "bootstrap/dist/js/bootstrap.js";
import "../styles/add_table_modal.css";
import { useAuth } from "../context/AuthContext";

function AddTableModal({ restaurantId, fetchTables }) {
  const { authDetails } = useAuth();

  const [seats, setSeats] = useState("");
  const handleRespponse = (response) => {
    console.log(response);
    console.log(response.success);
    if (response.success) {
      fetchTables();
      setSeats("");
      sendToast(true, response.data);
    } else {
      if ("data" in response) {
        sendToast(false, response.data);
      } else if ("errorMessage" in response) {
        sendToast(false, response.errorMessage);
      }
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    let requestBody = {
      managerUsername: authDetails.username,
      seatsNumber: seats,
    };
    console.log("managerUsername:", authDetails.username);
    console.log("seatsNumber:", seats);
    let res = await postData(
      "/tables/" + restaurantId + "/add",
      requestBody,
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
          id="addTableModal"
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
                  Add Table
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
                      htmlFor="seats-num"
                      className="col-form-label col-md-4"
                    >
                      Number of Seats
                    </label>

                    <input
                      type="number"
                      className="form-control ms-auto rounded-4"
                      name="name"
                      id="seats-num"
                      min="1"
                      value={seats}
                      onChange={(e) => setSeats(e.target.value)}
                    />
                  </div>

                  <button
                    type="submit"
                    id="add-table-submit-butoon"
                    className="btn w-100 btn-primary rounded-3"
                    data-bs-dismiss="modal"
                    disabled={seats === ""}
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

export default AddTableModal;
