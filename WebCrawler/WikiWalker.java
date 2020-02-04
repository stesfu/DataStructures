package wiki;

import java.util.*;

public class WikiWalker {

    private HashMap<String, HashMap<String,Integer>> siteMap; 

	WikiWalker() {
		this.siteMap = new HashMap<String, HashMap<String, Integer>>();

	}

    /**
     * Adds an article with the given name to the site map and associates the
     * given linked articles found on the page. Duplicate links in that list are
     * ignored, as should an article's links to itself.
     * 
     * @param articleName The name of the page's article
     * @param articleLinks List of names for those articles linked on the page
     */
	public void addArticle(String articleName, List<String> articleLinks) {
		siteMap.put(articleName, new HashMap<String, Integer>());
		for (int i = 0; i < articleLinks.size(); i++) {
			if (!articleLinks.get(i).contentEquals(articleName)
					&& !siteMap.get(articleName).containsKey(articleLinks.get(i))) {
				siteMap.get(articleName).put(articleLinks.get(i), 0);
			}

		}
	}

    /**
     * Determines whether or not, based on the added articles with their links,
     * there is *some* sequence of links that could be followed to take the user
     * from the source article to the destination.
     * 
     * @param src The beginning article of the possible path
     * @param dest The end article along a possible path
     * @return boolean representing whether or not that path exists
     */
	public boolean hasPath(String src, String dest) {
		return (containsPath(dest, src, new ArrayList<String>()));
	}

    /**
     * Increments the click counts of each link along some trajectory. For
     * instance, a trajectory of ["A", "B", "C"] will increment the click count
     * of the "B" link on the "A" page, and the count of the "C" link on the "B"
     * page. Assume that all given trajectories are valid, meaning that a link
     * exists from page i to i+1 for each index i
     * 
     * @param traj  A sequence of a user's page clicks; must be at least 2 article
     * 		  names in length
     */
	public void logTrajectory(List<String> traj) {
		String start;
		for (int i = 0, j = 1; i < traj.size() && j < traj.size(); i++, j++) {
			start = traj.get(i);
			siteMap.get(start).replace(traj.get(j), siteMap.get(start).get(traj.get(j)) + 1);
		}
	}
    
    /**
     * Returns the number of clickthroughs recorded from the src article to the
     * destination article. If the destination article is not a link directly
     * reachable from the src, returns -1.
     * 
     * @param src The article on which the clickthrough occurs.
     * @param dest The article requested by the clickthrough.
     * @throws IllegalArgumentException if src isn't in site map
     * @return The number of times the destination has been requested from the
     *         source.
     */
	public int clickthroughs(String src, String dest) {
		if (!siteMap.containsKey(src)) {
			throw new IllegalArgumentException();
		}
		if (!siteMap.get(src).containsKey(dest)) {
			return -1;
		} else {
			return siteMap.get(src).get(dest);
		}
	}

    /**
     * Based on the pattern of clickthrough trajectories recorded by this
     * WikiWalker, returns the most likely trajectory of k clickthroughs
     * starting at (but not including in the output) the given src article.
     * Duplicates and cycles are valid output along a most likely trajectory. In
     * the event of a tie in max clickthrough "weight," this method will choose
     * the link earliest in the ascending alphabetic order of those tied.
     * 
     * @param src The starting article of the trajectory (which will not be
     * 		  included in the output)
     * @param k The maximum length of the desired trajectory (though may be
     * 		  shorter in the case that the trajectory ends with a terminal
     * 		  article).
     * @return A List containing the ordered article names of the most likely
     *         trajectory starting at src.
     */
	public List<String> mostLikelyTrajectory(String src, int k) {
		List<String> list = new ArrayList<String>();
		String biggestStr = "";
		int count = 0;
		int biggestValue = 0;

		while (k > 0) {
			if (siteMap.containsKey(src)) {
				for (Map.Entry<String, Integer> innerMap : siteMap.get(src).entrySet()) {
					if (biggestStr.isEmpty()) {
						biggestStr = innerMap.getKey();
						biggestValue = innerMap.getValue();
					} else if (innerMap.getValue() > biggestValue) {
						biggestValue = innerMap.getValue();
						biggestStr = innerMap.getKey();
					} else if (innerMap.getValue() == biggestValue) {
						biggestValue = (biggestStr.compareToIgnoreCase(innerMap.getKey()) < 0) ? biggestValue
								: innerMap.getValue();
						biggestStr = (biggestStr.compareToIgnoreCase(innerMap.getKey()) < 0) ? biggestStr
								: innerMap.getKey();
					}
				}
				list.add(biggestStr);
				src = biggestStr;
				biggestStr = "";
				k--;

			} else {
				k = 0;
			}
		}
		return list;
	}
	   
  
    /**
     *  Determines whether or not, based on the added articles with their links,
     * there is *some* sequence of links that could be followed to take the user
     * from the source article to the destination.
     * @param dest The end article along a possible path
     * @param src The beginning article of the possible path
     * @param visited ArrayList of keys that were visited in path
     * @return boolean representing whether or not that path exists
     */
	private boolean containsPath(String dest, String src, ArrayList<String> visited) {
		if (src.equals(dest)) {
			return true;
		}
		if (visited.contains(src)) {
			return false;
		}
		visited.add(src);
		if (siteMap.containsKey(src)) {
			for (Map.Entry<String, Integer> innerMap : siteMap.get(src).entrySet()) {
				if (innerMap.getKey().contentEquals(dest)) {
					return true;
				}
				if (containsPath(dest, innerMap.getKey(), visited)) {
					return true;

				}
			}

		}
		return false;
	}

}
