package me.elb1to.scrape;

public class ScrapeOptions {
    private boolean hook;
    private boolean scoreDirectionDown;

    static ScrapeOptions defaultOptions() {
        return new ScrapeOptions().hook(false).scoreDirectionDown(false);
    }

    public boolean hook() {
        return this.hook;
    }

    public boolean scoreDirectionDown() {
        return this.scoreDirectionDown;
    }

    public ScrapeOptions hook(final boolean hook) {
        this.hook = hook;
        return this;
    }

    public ScrapeOptions scoreDirectionDown(final boolean scoreDirectionDown) {
        this.scoreDirectionDown = scoreDirectionDown;
        return this;
    }
}
