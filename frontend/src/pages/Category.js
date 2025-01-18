import React from 'react';
import { Link } from 'react-router-dom';
import politicsImg from '../images/politics.jpg';
import economyImg from '../images/economy.jpg';
import societyImg from '../images/society.jpg';
import cultureImg from '../images/culture.jpg';
import '../components/Category.css';

function Category() {
    const categories = [    
        { id: 1, keyword: "정치", link: "/category/politics", imageUrl: politicsImg },
        { id: 2, keyword: "경제" , link: "/category/economy", imageUrl: economyImg },
        { id: 3, keyword: "사회" , link: "/category/society", imageUrl: societyImg },
        { id: 4, keyword: "생활" , link: "/category/culture", imageUrl: cultureImg }
    ];

    return (
        <div className="App">
            <div className="categories-container">
                <h1 className="category-main-title">카테고리</h1>
                <div className="categories-grid">
                    {categories.map((category) => (
                        <Link 
                            to={category.link}
                            key={category.id}
                            className="category-link"
                        >
                            <div className="category-container">
                                <img 
                                    src={category.imageUrl} 
                                    alt={category.keyword}
                                    className="category-image"
                                />
                                <div className="category-title">{category.keyword}</div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default Category;
