import React from "react";
import CryptoJS from "crypto-js";
const SHA256 = require("crypto-js/sha256");
let md5 = require('md5')


export default function Register() {
    // registerStatusText is used to save the message whether the account is registered successfully or not
    const [registerStatusText, setRegisterStatusText] = React.useState("")

    // formData is used to save the user input values
    const [formData, setFormData] = React.useState(
        {
            username: "",
            password: ""
        }
    )

    // inputWarningText is used to save messages that prompts the user to change the information.
    const [inputWarningText, setInputWarningText] = React.useState({
        usernameReminder: "",
        passwordReminder: ""
    })

    /**
     * Check that the input conforms to the specification.
     * @param data
     * @returns {boolean}
     */
    function checkInput(data) {
        const regex = /[_\\-\\.0-9a-z]/
        let result = true
        for (let char of data) {
            if (char.match(regex) === null) {
                result = false
            }
        }
        return result && (data.length <= 127 && data.length >= 1)
    }

    function updateFormData(event) {
        event.preventDefault()
        console.log(event.target.value)
        setFormData(prevFormData => {
            return {
                ...prevFormData,
                [event.target.name]: event.target.value
            }
        })
    }

    function handleSubmit(event) {
        event.preventDefault()

        const nameIsValid = checkInput(formData.username)
        const passwordIsValid = checkInput(formData.password)

        setInputWarningText(prevState => ({
            usernameReminder: "",
            passwordReminder: ""
        }))

        if (nameIsValid && passwordIsValid) {
            setFormData({
                username: formData.username,
                password: formData.password
            })
            const salt = "80zzm081sr@nd0m";
            const algo = CryptoJS.algo.SHA256.create();
            algo.update(formData.password, "utf-8");
            algo.update(CryptoJS.SHA256(salt), "utf-8");
            const hash = algo.finalize().toString(CryptoJS.enc.hex);
            formData.password = md5(hash);
            fetch('http://localhost:8080/bank/user/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            })
                .then(response => response.json())
                .then(data => {
                    // setBalance(data.data.user.balance)
                    if (data.status === 200) {
                        setRegisterStatusText(`Congratulations ${formData.username}, You have registered successfully!`)
                    } else {
                        setRegisterStatusText(`This username has been used, please try another one!`)
                    }
                })
                .catch((error) => {
                    setRegisterStatusText(`Error: ${error}`)
                });
        }
        if (!nameIsValid || !passwordIsValid) {
            setInputWarningText({
                usernameReminder: nameIsValid ? "" : "Invalid account name",
                passwordReminder: passwordIsValid ? "" : "Invalid password"
            })
        }
    }

    return (
        <div className="register">
            <h1 className="register-title text-info">SIGN UP</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="username"
                    name="username"
                    onChange={updateFormData}
                    value={formData.username}
                    className="register-form-input"
                />
                <p className="register-form-reminder">{inputWarningText.usernameReminder}</p>


                <input
                    type="password"
                    placeholder="password"
                    name="password"
                    onChange={updateFormData}
                    value={formData.password}
                    className="register-form-input"
                />
                <p className="register-form-reminder">{inputWarningText.passwordReminder}</p>

                <button className="btn register-form-btn">Sign up</button>
            </form>
            <br/>
            <div className="register-instruction text-light">
                <p>Limitation to username and passwords:</p>
                <li>Between 1 and 127 in length</li>
                <li>Restricted to "_", ".", "-", digits, <br></br> and lowercase alphabetical characters</li>
            </div>
            <p className="register-statusText">{registerStatusText}</p>
        </div>
    )
}