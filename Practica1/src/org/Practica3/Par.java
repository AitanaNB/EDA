package org.Practica3;

public class Par {
	String web;
	Double pageRank;
	
	public Par(String web, double pageRank) {
        this.web = web;
        this.pageRank = pageRank;
    }

	public double getPageRank() {
		return pageRank;
	}
	
	public String getUrl() {
		return this.web;
	}
	
	@Override
    public String toString() {
        return "<" + web + ", " + pageRank + ">";
    }

}
