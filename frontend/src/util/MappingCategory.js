export const mapCategory = (category) => {
    const categoryMapping = {
      'politics': '정치',
      'economy': '경제',
      'society': '사회',
      'culture': '생활'
      
    };
  
    return categoryMapping[category] || category;
  };