import React from "react";

export default function Home(props) {
    const [balance, setBalance] = React.useState(props.balance)
    const [reminderText, setReminderText] = React.useState("")
    const token = props.token
    // postObj saves the userId and the amount of money entered in the input box
    const [postObj, setPostObj] = React.useState({
        id: props.id,
        amount: ""
    })

    /**
     * updateAmount() function is used to update the amount every time the user make changes
     * @param event
     */
    function updateAmount(event) {
        setPostObj(prevData => ({
            ...prevData,
            amount: event.target.value
        }))
    }

    /**
     * Deposit money
     */
    function deposit() {
        fetch('http://localhost:8080/bank/user/deposit', {
            method: 'POST',
            headers: {
                'token': token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postObj),
        })
            .then(response => response.json())
            .then(data => {
                if(data.status === 500){
                    setReminderText(data.msg)
                }else{
                    setReminderText(`Successfully deposited $${postObj.amount}!`)
                    setBalance(data.data)
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    /**
     * Withdrawal money
     */
    function withdrawal() {
        fetch('http://localhost:8080/bank/user/withdrawal', {
            method: 'POST',
            headers: {
                'token': token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postObj),
        })
            .then(response => response.json())
            .then(data => {
                if(data.status === 500){
                    setReminderText(data.msg)
                }else{
                    setReminderText(`Successfully withdraw $${postObj.amount}!`)
                    setBalance(data.data)
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    return (
        <div className="home">
            <div className="home-header text-info">
                <h1 className="home-title">Account <span className="home-title-username">{props.username}</span></h1>
            </div>


            <p className="home-balance"> ${balance}</p>
            <p className="home-text"> Available balance</p>


            <input
                placeholder={`Enter transaction amount`}
                onChange={updateAmount}
                name="amount"
                value={postObj.amount}
                className="home-form-input"
            />
            <p className="home-reminderText">{reminderText}</p>
            <button className="btn home-deposit-btn" onClick={deposit}>Deposit</button>
            <button className="btn home-withdrawal-btn" onClick={withdrawal}>Withdraw</button>
            <div>
                <newcontainer>
                    <button className="btn home-logout-btn" onClick={props.changeLoginStatus}>Log out</button>
                </newcontainer>
            </div>

        </div>
    )
}