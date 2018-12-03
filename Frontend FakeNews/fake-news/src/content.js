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
        if(this.props.tweetData.tweetId === this.props.attr) {
            this.setState({
                text: this.handleStatus(),
                previousText: this.handleStatus()
            });
        }
    }

    handleStatus() {
        if( this.props.tweetData.status > 50 ) {
            return "True";
        } else if( this.props.tweetData.status < 50 ) {
            return "Fake";
        } else {
            return "Neutral";
        }
    }

    onMouseover(e) {
        this.setState({text : this.props.tweetData.status + "%"})
    }

    onMouseout(e) {
        this.setState({text : this.state.previousText})
    }

    render() {
        const { text } = this.state;
        return (
            <Fragment>
                    <div className={"classifier-style " + this.state.previousText.toLowerCase()} onMouseEnter={this.onMouseover} onMouseLeave={this.onMouseout}>
                        { text }
                    </div>  
            </Fragment>
        )
    }
}

const gambitGalleryIsInView = el => {
	const scroll = window.scrollY || window.pageYOffset
	const boundsTop = el.getBoundingClientRect().top + scroll
	
	const viewport = {
		top: scroll,
		bottom: scroll + window.innerHeight,
	}
	
    const bounds = {
		top: boundsTop,
		bottom: boundsTop + el.clientHeight,
	}
	
	return ( bounds.bottom >= viewport.top && bounds.bottom <= viewport.bottom ) 
		|| ( bounds.top <= viewport.bottom && bounds.top >= viewport.top );
}

const sendListOfId = (prev, counter, nrOfTweets) => {
    let list = [];
    const copyCounter = counter;
    counter = prev + counter;

    while(counter <= nrOfTweets) {
        for(let i = prev; i < counter; i++) {
            let tweet = document.getElementsByClassName('js-stream-tweet')[i];
            let attribute = tweet.getAttribute('data-tweet-id');
            list.push(attribute);
        }
        console.log(list);
        list = [];
        prev = counter;

        if(nrOfTweets - counter < copyCounter && nrOfTweets - counter !== 0) {
            counter = nrOfTweets;
        } else {
            counter += copyCounter;
        }
    }
}

setTimeout(() => {
    let nrOfTweets = document.getElementsByClassName('js-stream-tweet').length;
    let counter = 5;
    console.log(nrOfTweets);

    sendListOfId(0, counter, nrOfTweets);

    let tweetVisible = document.getElementsByClassName('js-stream-tweet')[nrOfTweets - 1];
    let ok = 0;

    const handler = () => {
        if(gambitGalleryIsInView( tweetVisible ) && ok === 0) {
            let tweetNr = document.getElementsByClassName('js-stream-tweet').length;
            console.log(nrOfTweets , tweetNr);
            if(nrOfTweets < tweetNr) {
                sendListOfId(nrOfTweets, counter, tweetNr);
                ok = 1;
                tweetVisible = document.getElementsByClassName('js-stream-tweet')[tweetNr - 1];
                nrOfTweets = tweetNr;
            }
        } else {
            ok = 0;
        }
    }
    
    window.addEventListener( 'scroll', handler )
}, 1000);


const data = () => {
    return fetch(url, {
            method: 'GET'
            }).then(response => response.json())
            .then(json => json)
            .catch(error => error);
}

// data().then((tweetData) => {
//     console.log(tweetData);
//     //console.log("Tweets: ", document.getElementsByClassName('tweet').length);
//     console.log(document.getElementsByClassName('tweet').length);
//     let i = 0;
//     let counter = 0;
//     let interval2 = setInterval(() => {
//         counter += 5;

//         let interval = setInterval(() => {
//             const tweet = document.getElementsByClassName('tweet')[i];
//             let attribute = tweet.getAttribute('data-tweet-id');
//             const app =  document.getElementsByClassName('context')[i];
//             app.id = "context-id";
//             ReactDOM.render(<Main tweetData={tweetData[i]} attr={attribute} />, app);
//             i++;
//             if(i === counter) {
//                 clearInterval(interval);
//                 i = counter;
//             } 
//         }, 20);

//         if (counter >= 20) {
//             clearInterval(interval2);
//         }
//     }, 500);
// })
