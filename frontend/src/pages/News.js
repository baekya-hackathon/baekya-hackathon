import React from 'react';
import styled from 'styled-components';

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

function News() {
  const news = {
    title: "뉴스 제목",
    keywords: ["키워드1", "키워드2", "키워드3"],
    content: "뉴스 내용이 여기에 들어갑니다..."
  };

  return (
    <NewContainer>
      <Header>
        <Keywords>
          {news.keywords.map((keyword, index) => (
            <Keyword key={index}>{keyword}</Keyword>
          ))}
        </Keywords>
      </Header>
      <Body>
        <Content>
          <NewsCard>
            <Title>{news.title}</Title>
            <NewsContent>{news.content}</NewsContent>
          </NewsCard>
        </Content>
      </Body>
    </NewContainer>
  );
}

export default News;