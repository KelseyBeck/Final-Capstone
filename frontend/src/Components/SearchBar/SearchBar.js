import React from 'react';
import './searchbar.css'

class SearchBar extends React.Component {
    
  constructor(props) {
    super(props);

    this.state = {
      term: '',
      location: '',
    };

    this.handleTermChange = this.handleTermChange.bind(this);
    this.handleLocationChange = this.handleLocationChange.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
  }

  handleTermChange(event) {
    this.setState({term: event.target.value});
  }

  handleLocationChange(event) {
    this.setState({location: event.target.value});
  }

  handleSearch(event) {
    event.preventDefault();
    this.props.searchYelp(this.state.term, this.state.location);
  }

  render() {
    return (
      <>
      
      <div className='searchbar'>
      <div>
      <h3>Search for Restaurants</h3>
      </div>
      <div>
          <input placeholder="type of food" onChange={this.handleTermChange} />
          <input placeholder="location" onChange={this.handleLocationChange}/>
          <button onClick={this.handleSearch}>Search</button>
          </div>
      </div>
      </>
    );
  }
}

export default SearchBar;