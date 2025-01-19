import React, { useState } from 'react';
import axios from 'axios';
import '../components/Search.css';

function Search() {
  const [searchTerm, setSearchTerm] = useState('');
  const [result, setResult] = useState('ㅇㄴㅁㅇㄴㅁㅇㄴ');

  const searchPerplexity = async (query) => {

    try {
      const response = await axios.post('https://api.perplexity.ai/chat/completions', {
        model: 'llama-3.1-sonar-small-128k-online',
        messages: [
          {
            role: 'system',
            content: 'Be precise and concise in your responses.'
          },
          {
            role: 'user',
            content: searchTerm
          }
        ]
      }, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': `Bearer {api key}`,
        }
      });
      
      setResult(response.data.choices[0].message.content);
    } catch (error) {
      console.error('검색 중 오류 발생:', error);
      setResult('검색 중 오류가 발생했습니다.');
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    searchPerplexity(searchTerm);
  };

  return (
    <div className="main-container">
        {result && (
          <div className="result-container">
            <p>{result}</p>
          </div>
        )}
      <div className="content-box">
        <form onSubmit={handleSearch} className="search-form">
          <input
            type="text"
            placeholder="검색어를 입력하세요"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          <button type="submit" className="search-button">
            검색
          </button>
        </form>
        
      </div>
    </div>
  );
}

export default Search; 