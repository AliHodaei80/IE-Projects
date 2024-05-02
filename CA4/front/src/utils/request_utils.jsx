import axios from 'axios';

function fetchData(url) {
    axios.get(url)
        .then(response => {
            console.log('Data:', response.data);
        })
        .catch(error => {
            if (error.response) {
                console.log('Error code:', error.response.status);
                console.log('Error message:', error.response.data.message);
                return { errorCode: error.response.status, errorMessage: error.response.data.message };
            } else if (error.request) {
                console.log('Error message:', error.message);
                return { errorCode: null, errorMessage: error.message };
            } else {
                console.log('Error:', error.message);
                return { errorCode: null, errorMessage: error.message };
            }
        });
}

function postData(url) {
    axios.get(url)
        .then(response => {
            console.log('Data:', response.data);
        })
        .catch(error => {
            if (error.response) {
                console.log('Error code:', error.response.status);
                console.log('Error message:', error.response.data.message);
                return { errorCode: error.response.status, errorMessage: error.response.data.message };
            } else if (error.request) {
                console.log('Error message:', error.message);
                return { errorCode: null, errorMessage: error.message };
            } else {
                console.log('Error:', error.message);
                return { errorCode: null, errorMessage: error.message };
            }
        });
}

export default fetchData;
