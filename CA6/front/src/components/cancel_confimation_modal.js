import React from "react";
import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { postData, sendToast } from "../utils/request_utils.js";
import "../styles/cancel_reservation_modal.css";
function CancelConfirmationModal({
  reservationId,
  restaurantName,
  unSetCancelReservationId,
}) {
  const { authDetails } = useAuth();
  const [agreeToCancel, setAgreeToCancel] = useState(false);

  const handleRespponse = (response) => {
    console.log(response);
    console.log(response.success);
    if (response.success) {
      setAgreeToCancel(false);
      unSetCancelReservationId();
      sendToast(true, response.data);
    } else {
      if ("data" in response) {
        sendToast(false, response.data);
      } else if ("errorMessage" in response) {
        sendToast(false, response.errorMessage);
      }
    }
  };

  const handleConfirmCancel = (event) => {
    event.preventDefault();

    let requestBody = {
      username: authDetails.user.username,
    };
    console.log(`Reservation ${reservationId} cancelled`);
    let res = postData(
      "/reservations/" + reservationId + "/cancel",
      requestBody,
      handleRespponse,
      () => {},
      () => {}
    );
  };
  const handleAgreeChange = (e) => {
    setAgreeToCancel(e.target.checked);
  };

  return (
    <>
      {
        <div
          className="modal fade"
          id="cancelConfirmationModal"
          role="dialog"
          data-bs-backdrop="static"
          data-bs-keyboard="false"
          tabIndex="-1"
          aria-labelledby="staticBackdropLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  Cancel Reservation at{" "}
                  <span id="restaurant-name-cancelation">{restaurantName}</span>
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>

              <div className="modal-body">
                <form onSubmit={handleConfirmCancel}>
                  <p id="reserve-canelation-text">
                    Note: Once you hit the Cancel button, your reserve will be
                    canceled
                  </p>
                  <label>
                    <input
                      id="agree-cancelation-check-box"
                      type="checkbox"
                      checked={agreeToCancel}
                      onChange={handleAgreeChange}
                    />{" "}
                    I agree
                  </label>
                  <button
                    type="submit"
                    id="cancelation-submit-btn"
                    className="btn btn-primary w-100 rounded-4 mt-2"
                    disabled={!agreeToCancel}
                    data-bs-dismiss="modal"
                  >
                    Confirm
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

export default CancelConfirmationModal;
