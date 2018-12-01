import React, { Component, Fragment } from 'react';
import ReactDOM from 'react-dom';
import { url } from './getData';
import "./content.css";


class Main extends Component {
    constructor(props) {
        super(props);

        this.state = {
            text: '',
            previousText: ''
        }

        this.onMouseover = this.onMouseover.bind(this);
        this.onMouseout = this.onMouseout.bind(this);
        this.handleStatus = this.handleStatus.bind(this);
    }

    componentDidMount() {
        this.setState({
            text: this.handleStatus(),
            previousText: this.handleStatus()
        })
    }

    handleStatus() {
        if( this.props.status.status > 50 ) {
            return "True";
        } else if( this.props.status.status < 50 ) {
            return "Fake";
        } else {
            return "Neutral";
        }
    }

    onMouseover(e) {
        this.setState({text : this.props.status.status + "%"})
    }

    onMouseout(e) {
        this.setState({text : this.state.previousText})
    }

    render() {
        const { text } = this.state;
        return (
            <div className={"classifier-style " + this.state.previousText.toLowerCase()} onMouseEnter={this.onMouseover} onMouseLeave={this.onMouseout}>
                { text }
            </div> 
        )
    }
}

const data = () => {
    return fetch(url, {
            method: 'GET'
            }).then(response => response.json())
            .then(json => json)
            .catch(error => error);
}

data().then((status) => {
    console.log(status);
    let i = 0;
    let counter = 20;
    
    let interval = setInterval(() => {
        const app =  document.getElementsByClassName('context')[i];
        if(app.querySelector(".conversation-module") === null){
            app.id = "context-id";
            ReactDOM.render(<Main status={status[i]}/>, app);
            i++;
            if(i === counter) {
                clearInterval(interval);
            } 
        } else {
            i++;
            counter++;
        }
    }, 20);
})
