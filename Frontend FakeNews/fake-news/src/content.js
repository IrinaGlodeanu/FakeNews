import React, { Component, Fragment } from 'react';
import ReactDOM from 'react-dom';
import { url } from './getData';
import "./content.css";

class Main extends Component {
    constructor(props) {
        super(props);

        this.state = {
            text: this.props.tweetData.tweetId === this.props.attr ? this.handleStatus() : "Idk",
            previousText: this.props.tweetData.tweetId === this.props.attr ? this.handleStatus() : "Idk"
        }

        this.onMouseover = this.onMouseover.bind(this);
        this.onMouseout = this.onMouseout.bind(this);
        this.handleStatus = this.handleStatus.bind(this);
        this.changeColor = this.changeColor.bind(this);
    }

    // componentDidMount() {
    //     if(this.props.tweetData.tweetId === this.props.attr) {
    //         this.setState({
    //             text: this.handleStatus(),
    //             previousText: this.handleStatus()
    //         });
    //     }
    // }

    handleStatus() {
        if( this.props.tweetData.status > 50 ) {
            return "True";
        } else if( this.props.tweetData.status < 50 ) {
            return "Fake";
        } else {
            return "Neutral";
        }
    }

    changeColor() {
        if(this.props.tweetData.status >= 0 && this.props.tweetData.status < 15) {
            return "";
        } 
        else if(this.props.tweetData.status >= 15 && this.props.tweetData.status < 30) {
            return "fake-15";
        }
        else if(this.props.tweetData.status >= 30 && this.props.tweetData.status < 50) {
            return "fake-30";
        }
        else if(this.props.tweetData.status > 50 && this.props.tweetData.status < 65) {
            return "true-65";
        }
        else if(this.props.tweetData.status >= 65 && this.props.tweetData.status < 80) {
            return "true-80";
        }
        else if(this.props.tweetData.status >= 80 && this.props.tweetData.status <= 100) {
            return "";
        }
    }

    onMouseover(e) {
        this.setState({text : this.props.tweetData.tweetId === this.props.attr ? this.props.tweetData.status + "%" : "Idk"})
    }

    onMouseout(e) {
        this.setState({text : this.state.previousText})
    }

    render() {
        const { text } = this.state;
        return (
            <Fragment>
                    <div className={"classifier-style " + this.state.previousText.toLowerCase() + " " + this.changeColor()} onMouseEnter={this.onMouseover} onMouseLeave={this.onMouseout}>
                        { text }
                        <span class="popup">Number of related news: {this.props.tweetData.numberOfRelatedNews}<br />Number of related publications: {this.props.tweetData.numberOfRelatedBigPublications}</span>
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

const createList = (count, nrOfTweets) => {
    let list = [];
    for(let i = count; i < nrOfTweets; i++) {
        let tweet = document.getElementsByClassName('js-stream-tweet')[i];
        let attribute = tweet.getAttribute('data-tweet-id');
        list.push(attribute);
    }
    return list;
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
        console.log("My List:", list);

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
    //let counter = 5;
    let myList = [];
    console.log(nrOfTweets);

    //sendListOfId(0, counter, nrOfTweets);
    myList = createList(0, nrOfTweets);

    let tweetVisible = document.getElementsByClassName('js-stream-tweet')[nrOfTweets - 1];
    let ok = 0;

    const handler = () => {
        if(gambitGalleryIsInView( tweetVisible ) && ok === 0) {
            let tweetNr = document.getElementsByClassName('js-stream-tweet').length;
            console.log(nrOfTweets , tweetNr);
            if(nrOfTweets < tweetNr) {
                // sendListOfId(nrOfTweets, counter, tweetNr);
                myList = createList(nrOfTweets, tweetNr);
                let copyList = [];
                for(let i = 0; i < myList.length; i++) {
                    copyList.push(myList[i]);
                    
                    if(copyList.length === 5) {
                        console.log(copyList);
                        copyList = []
                    }

                    if(copyList.length < 5 && i === myList.length - 1 ){
                        console.log(copyList);
                        copyList = []
                    }
                }
                ok = 1;
                tweetVisible = document.getElementsByClassName('js-stream-tweet')[tweetNr - 1];
                nrOfTweets = tweetNr;
            }
        } else {
            ok = 0;
        }
    }

    let copyList = [];
    for(let i = 0; i < myList.length; i++) {
        copyList.push(myList[i]);
        if((copyList.length === 3) || (copyList.length < 3 && i === myList.length - 1 )) {
            send(copyList, (tweetData) => {
                console.log(tweetData);
                for(let j = 0; j < tweetData.length; j++) {
                    const tweet = document.getElementsByClassName('tweet')[i-2+j];
                    let attribute = tweet.getAttribute('data-tweet-id');
                    const app =  document.getElementsByClassName('context')[i-2+j];
                    app.id = "context-id";
                    ReactDOM.render(<Main tweetData={tweetData[j]} attr={attribute} />, app);
                }
            })
            copyList = [];
        } 
    }


    window.addEventListener( 'scroll', handler );

}, 1000);


const data = () => {
    return fetch(url, {
            method: 'GET'
            }).then(response => response.json())
            .then(json => json)
            .catch(error => error);
}

const send = (list, callback) => {
    fetch("http://fakenews-env-1.p5ymp76gg5.eu-central-1.elasticbeanstalk.com/api/v1/newsApi/batchStatus", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            },
            body: JSON.stringify({
                "listOfIds": list
              })
            }).then(response => response.json())
            .then(json => callback(json))
            .catch(error => console.log("Error", error));
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
