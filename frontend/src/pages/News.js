import React from 'react';
import styled from 'styled-components';
import { Button } from 'react-bootstrap';
import { BsFillVolumeUpFill } from 'react-icons/bs';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';

const NewContainer = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const Header = styled.div`
  padding: 20px;
  background-color: #f5f5f5;
  border-bottom: 1px solid #e0e0e0;
`;

const Keywords = styled.div`
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
`;

const Keyword = styled.span`
  background-color: rgba(126, 55, 249, 0.1);
  padding: 12px 20px;
  border-radius: 20px;
  font-size: 18px;
  color: #7E37F9;
  font-weight: bold;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
  
  &:hover {
    background-color: rgba(126, 55, 249, 0.2);
    transform: translateY(-2px);
    box-shadow: 0 2px 8px rgba(126, 55, 249, 0.2);
  }
`;

const Body = styled.div`
  padding: 20px;
  padding-bottom: 80px;
  flex: 1;
  overflow-y: auto;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const NewsCard = styled.div`
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: all 0.2s ease-in-out;
  cursor: pointer;
  
  &:hover {
    box-shadow: 0 4px 12px rgba(126, 55, 249, 0.25);
    transform: translateY(-2px);
  }
`;

const Title = styled.h2`
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e0e0e0;
`;

const NewsContent = styled.p`
  font-size: 20px;
  font-weight: 500;
  line-height: 1.5;
  padding-top: 10px;
`;

const NewsUrl = styled.a`
  color: #7E37F9;
  text-decoration: none;
  font-size: 16px;
  margin-top: 10px;
  display: block;
  
  &:hover {
    text-decoration: underline;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
`;

const SpeakerButton = styled(Button)`
  background-color: #7E37F9;
  border: none;
  padding: 8px 12px;
  border-radius: 20px;
  
  &:hover {
    background-color: #6422e0;
  }
`;

function News() {
  
  const { id } = useParams();

  const [news, setNews] = useState({
    title: "뉴스 제목",
    keyword: ["키워드1", "키워드2", "키워드3"],
    summary: "뉴스 내용이 여기에 들어갑니다...",
    newsLink: "https://www.google.com"
  });

  
  useEffect(() => {
    let params = new URLSearchParams(window.location.search);

    // id 값 추출
    let idValue = params.get('id');
    console.log(idValue);
    const fetchNewsDetail = async () => { 
      const response = await axios.get(`https://easynews.o-r.kr/v1/category/${idValue}`);
      
       let item = response.data.data;
      const newsData = {
        id: item.id,
        title: item.title,
        summary: item.summary,
        newsLink: item.newsLink,
        keyword: item.keyword
            .replace(/[\[\]]/g, '') // 대괄호 제거
            .split(',')             // 쉼표로 분리
            .map(k => k.trim())     // 각 항목의 앞뒤 공백 제거
      }
      
      setNews(newsData);
    };
    fetchNewsDetail();
  }, []);
  

  const handleSpeak = () => {
    const speech = new SpeechSynthesisUtterance(news.summary);
    window.speechSynthesis.speak(speech);
  };

  return (
    <NewContainer>
      <Header>
        <Keywords>
          {news.keyword.map((keyword, index) => (
            <Keyword key={index}>{keyword}</Keyword>
          ))}
        </Keywords>
        <div>
         
        </div>
      </Header>
      <Body>
        <Content>
          <NewsCard>
            <Title>{news.title}</Title>
            <NewsContent>{news.summary}</NewsContent>
            <ButtonContainer>
              <NewsUrl href={news.newsLink} target="_blank" rel="noopener noreferrer">
                원문 보기
              </NewsUrl>
              <SpeakerButton onClick={handleSpeak}>
                <BsFillVolumeUpFill />
              </SpeakerButton>
            </ButtonContainer>
          </NewsCard>
        </Content>
      </Body>
    </NewContainer>
  );
}

export default News;