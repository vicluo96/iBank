import React from "react";
import CryptoJS from "crypto-js";
const SHA256 = require("crypto-js/sha256");
let md5 = require('md5')
export default function Login(props) {
    const [loginFormData, setLoginFormData] = React.useState(
        {
            username: "",
            password: ""
        }
    )
    const [loginStatusText, setLoginStatusText] = React.useState("")

    /**
     * loginFormData will be updated everytime the username or password entered are changed
     * @param event
     */
    function updateUserLoginData(event) {
        event.preventDefault()
        setLoginFormData(prevFormData => {
            return {
                ...prevFormData,
                [event.target.name]: event.target.value
            }
        })
    }

    /**
     * When user submit the form, send loginFormData through API to verify identity
     * @param event
     */
    function handleSubmit(event) {
        event.preventDefault()
        setLoginFormData({
            username: loginFormData.username,
            password: loginFormData.password
        })
        const salt = "80zzm081sr@nd0m";
        const algo = CryptoJS.algo.SHA256.create();
        algo.update(loginFormData.password, "utf-8");
        algo.update(CryptoJS.SHA256(salt), "utf-8");
        const hash = algo.finalize().toString(CryptoJS.enc.hex);
        loginFormData.password = md5(hash);
        fetch('http://localhost:8080/bank/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginFormData),
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 200) {
                    setLoginStatusText("Login Successfully!")
                    // Call the changeLoginStatus() function passed from App.js to update the user login data
                    props.updateUserInfo(data)
                } else if (data.status === 500) {
                    /*BAD CODE START*/
                    //setLoginStatusText(data.msg)
                    /*BAD CODE END*/
                    setLoginStatusText("Login failed: invalid input or unsupported format")
                }
            })
            .catch((error) => {
                setLoginStatusText(`Error: ${error}`)
            });
    }

    return (
        <div className="login">
            <h1 className="login-title, text-info">MEMBER LOGIN</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="username"
                    name="username"
                    onChange={updateUserLoginData}
                    value={loginFormData.username}
                    className="login-form-input"
                />
                <input
                    type="password"
                    placeholder="password"
                    name="password"
                    onChange={updateUserLoginData}
                    value={loginFormData.password}
                    className="login-form-input"
                />
                <button className="btn login-form-btn">Log in</button>
            </form>
            <p className="login-form-reminder">{loginStatusText}</p>
        </div>
    )
}
