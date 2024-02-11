
import { makeRequest } from "../commons/api.js";

//wait for page to load using DOMContentLoad
if (window.location.pathname.includes('/crs/login')){
document.addEventListener('DOMContentLoaded', () => {
    const form =  document.getElementById("login-form");

    //Add event listener on form
    form.addEventListener('submit', onSubmit);
});
}
const onSubmit = (event) => {

    event.preventDefault();

    // Fetch Form-data
    const formData = new FormData(event.target);
    const credentials = Object.fromEntries(formData.entries());

    // Invoke makeRequest(url, method, formData, onSuccess, onError)
    makeRequest("/crs/login", "POST", credentials, onSuccess, onError);
}

const onSuccess = (response) => {
    window.location.href = '/crs';
}

const onError = (response) => {
    window.location.href = '/crs/login?status=FAILED';
}

export { onSubmit }
