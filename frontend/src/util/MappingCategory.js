export const mapCategory = (category) => {
    const categoryMapping = {
      'politics': '정치',
      'economy': '경제',
      'social': '사회',
      'life': '생활'
      
    };
  
    return categoryMapping[category] || category;
  };