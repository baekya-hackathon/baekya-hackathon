import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';
import { mapCategory } from '../util/MappingCategory';
import InfiniteScroll from 'react-infinite-scroll-component';

const Container = styled.div`
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 15px;
`;

const PageTitle = styled.h1`
    text-align: center;
    margin: 5px 0 40px 0;
    color: #7E37F9;
    font-size: 2.8rem;
    font-weight: 600;
    position: relative;
    padding-bottom: 10px;
    font-family: 'Noto Sans KR', sans-serif;
    width: 100%;
    
    &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 60px;
        height: 4px;
        background: #7E37F9;
        border-radius: 2px;
    }
`;

const NewsTitle = styled.h3`
    color: #333;
    margin: 0;
    font-size: 24px;
    text-align: center;
    padding: 0 20px 20px 20px;
    font-weight: 800;
    line-height: 1.4;
    font-family: 'Noto Sans KR', sans-serif;
    border-bottom: 1px solid #e0e0e0;
    margin-bottom: 20px;
`;

const KeywordCloud = styled.div`
    background-color: white;
    border-radius: 12px;
    padding: 30px;
    box-shadow: 0 2px 8px rgba(126, 55, 249, 0.1);
    min-height: 250px;
    height: auto;
    position: relative;
    display: flex;
    flex-direction: column;
    cursor: pointer;
    transition: all 0.2s ease;
    margin: 0 auto;
    width: 100%;

    &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 12px rgba(126, 55, 249, 0.3);
    }
`;

const KeywordContainer = styled.div`
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 10px;
    min-height: 100px;
`;

const Keywords = styled.div`
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    width: 100%;
    padding: 10px;
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

function NewsList() {
    const [items, setItems] = useState([]);
    const [hasMore, setHasMore] = useState(true);
    const [page, setPage] = useState(0);
    
    const news = [
        {
            id: 1,
            title: "삼성전자, 갤럭시 S24 시리즈 AI 기능 공개",
            keyWord: ['삼성전자', '갤럭시', 'AI', '스마트폰'],
        },
        {
            id: 2,
            title: "네이버, 초거대 AI 'HyperCLOVA X' 공개",
            keyWord: ['네이버', 'AI', '하이퍼클로바', '인공지능'],
        },
        {
            id: 3,
            title: "SK텔레콤, AI 반도체 개발 착수",
            keyWord: ['SK텔레콤', '반도체', 'AI칩', '투자'],
        },
        {
            id: 4,
            title: "카카오, AI 기반 신규 서비스 출시 예고",
            keyWord: ['카카오', 'AI서비스', '카카오톡', '테크'],
        },
        {
            id: 5,
            title: "LG전자, AI 가전제품 라인업 확대",
            keyWord: ['LG전자', 'AI가전', '스마트홈', 'IoT'],
        },
        {
            id: 6,
            title: "현대자동차, AI 자율주행 기술 고도화",
            keyWord: ['현대자동차', '자율주행', 'AI기술', '모빌리티'],
        }
    ];

    const location = useLocation();
    const currentUrl = location.pathname;
    const category = currentUrl.split('/').pop();

    const navigate = useNavigate();

    useEffect(() => {
        fetchMoreData();
    }, []);

    const fetchMoreData = () => {
        const itemsPerPage = 4;
        const startIndex = page * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        
        const newItems = news.slice(startIndex, endIndex);
        
        if (endIndex >= news.length) {
            setHasMore(false);
        }

        setItems(prevItems => [...prevItems, ...newItems]);
        setPage(prevPage => prevPage + 1);
    };

    const checkOverlap = (newPos, existingPositions, newWidth, newHeight) => {
        for (let pos of existingPositions) {
            // 각 요소의 경계를 확인
            const horizontalOverlap = 
                newPos.x < (pos.x + pos.width + 10) && 
                (newPos.x + newWidth + 10) > pos.x;
            const verticalOverlap = 
                newPos.y < (pos.y + pos.height + 10) && 
                (newPos.y + newHeight + 10) > pos.y;

            if (horizontalOverlap && verticalOverlap) {
                return true; // 겹침 발생
            }
        }
        return false; // 겹치지 않음
    };

    return (
        <>
            <PageTitle>{mapCategory(category) || 'NewsList'}</PageTitle>
            <InfiniteScroll
                dataLength={items.length}
                next={fetchMoreData}
                hasMore={hasMore}
                loader={
                    <div style={{ textAlign: 'center', padding: '20px' }}>
                        Loading...
                    </div>
                }
                endMessage={
                    <div style={{ textAlign: 'center', padding: '20px' }}>
                        모든 뉴스를 불러왔습니다.
                    </div>
                }
                style={{ overflow: 'hidden' }}
            >
                <div style={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '30px',
                    width: '100%',
                    maxWidth: '1000px',
                    padding: '30px',
                    margin: '0 auto'
                }}>
                    {items.map((item, index) => (
                        <KeywordCloud 
                            key={index}
                            onClick={() => navigate(`/new/?id=${item.id}&category=${category}`)}
                        >
                            <NewsTitle>{item.title}</NewsTitle>
                            <KeywordContainer>
                                <Keywords>
                                    {item.keyWord.map((keyword, kidx) => (
                                        <Keyword key={kidx}>
                                            {keyword}
                                        </Keyword>
                                    ))}
                                </Keywords>
                            </KeywordContainer>
                        </KeywordCloud>
                    ))}
                </div>
            </InfiniteScroll>
        </>
    );
}

export default NewsList;